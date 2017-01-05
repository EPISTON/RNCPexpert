package com.courtalon.gigaMvcGalerie.repositories;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.transaction.annotation.Transactional;

import com.courtalon.gigaMvcGalerie.metier.Image;
import com.courtalon.gigaMvcGalerie.utils.FileStorageManager;

import javassist.bytecode.ByteArray;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;


public class ImageRepositoryImpl implements ImageRepositoryCustom
{
	private static Logger log = LogManager.getLogger(ImageRepositoryImpl.class);
	private static final int THUMBWIDTH = 164;
	private static final int THUMBHEIGHT= 164;
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	
	@Autowired
	private FileStorageManager fileStorageManager;
	public FileStorageManager getFileStorageManager() {return fileStorageManager;}
	public void setFileStorageManager(FileStorageManager fileStorageManager) {this.fileStorageManager = fileStorageManager;}

	@Override
	public boolean saveImageFile(int id, File f) {
		// "Image" -> nom du depot, "id" -> id image, "f" -> fichier de l'image 
		if (getFileStorageManager().saveFile("Image", id, f)) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Thumbnails.of(f)
					      .size(THUMBWIDTH, THUMBHEIGHT)
					      .useOriginalFormat()
					      .scalingMode(ScalingMode.BICUBIC)
					      .antialiasing(Antialiasing.ON)
					      .toOutputStream(bos);
				return getFileStorageManager().saveFile("ImageThumb", id,
						new ByteArrayInputStream(bos.toByteArray()));
			} catch (IOException e) {log.error(e);}
		}
		return false;
	}
	@Override
	public boolean saveImageFile(int id, InputStream s) {
		if (getFileStorageManager().saveFile("Image", id, s)) {
		try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Thumbnails.of(getFileStorageManager().getFile("Image", id).get())
						  .size(THUMBWIDTH, THUMBHEIGHT)
						  .useOriginalFormat()
						  .scalingMode(ScalingMode.BICUBIC)
					      .antialiasing(Antialiasing.ON)
						  .toOutputStream(bos);
				return getFileStorageManager().saveFile("ImageThumb", id,
							new ByteArrayInputStream(bos.toByteArray()));
			} catch (IOException e) {log.error(e);}
		}
		return false;
	}
	
	
	@Override
	public boolean removeImageFile(int id) {
		return getFileStorageManager().removeFile("Image", id);
	}
	@Override
	public boolean removeImageThumbFile(int id) {
		return getFileStorageManager().removeFile("ImageThumb", id);
	}
	
	@Override
	public Optional<File> getImageFile(int id) {
		return getFileStorageManager().getFile("Image", id);
	}
	
	@Override
	public Optional<File> getImageThumbFile(int id) {
		return getFileStorageManager().getFile("ImageThumb", id);
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public Page<Image> findByTagList(List<Integer> tagids, Pageable page, boolean includeTags) {
		
		String countRequest = "select count(i.id) from Image as i";
		String pageRequest = "select DISTINCT i from Image as i";
		if (includeTags)
			pageRequest += " left join fetch i.tags";
		
		StringBuilder ejbstring = new StringBuilder("");
		if (tagids != null && tagids.size() > 0)
		{
			int nb_params = tagids.size();
			for (int i = 1; i <= nb_params; i++) {
				ejbstring.append(", IN(i.tags) ta");
				ejbstring.append(i);
			}
			ejbstring.append(" WHERE");
			for (int i = 1; i <= nb_params; i++)
			{
				if (i > 1)
					ejbstring.append(" AND");
				ejbstring.append(" ta");
				ejbstring.append(i);
				ejbstring.append(".id = :tp");
				ejbstring.append(i);
			}
		}
		countRequest += ejbstring.toString();
		pageRequest += ejbstring.toString();
		
		// ajout du tri si nécéssaire
		if (page.getSort() != null && page.getSort().iterator().hasNext()) {
			Order o = page.getSort().iterator().next();
			String propName = o.getProperty();
			String direction = o.getDirection().toString();
			pageRequest += " ORDER BY i." + propName + " " + direction;
		}
		
		log.info(countRequest);
		log.info(pageRequest);
		// creation des requettes de comptage et de donnée
		TypedQuery<Long> qCount = em.createQuery(countRequest, Long.class);
		TypedQuery<Image> qData = em.createQuery(pageRequest, Image.class);
		
		// binding des parametres
		int index_param = 1;
		for (int tid : tagids)
		{
			qCount.setParameter("tp" + index_param, tid);
			qData.setParameter("tp" + index_param, tid);
			index_param++;
		}
		// sur la requette donnée, je fait la pagination
		qData.setFirstResult(page.getOffset());
		qData.setMaxResults(page.getPageSize());
		
		// je rerourne un objet Page contenant mes données, et le nombre total d'élément
		PageImpl<Image> pResult = new PageImpl<>(qData.getResultList(), page, qCount.getSingleResult());
		return pResult;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Image findOneIncludingTags(int id) {
		TypedQuery<Image> q = em.createQuery("select i from Image as i left join fetch i.tags where i.id=:imageId", Image.class);
		q.setParameter("imageId", id);
		return q.getSingleResult();
	}
	

}
