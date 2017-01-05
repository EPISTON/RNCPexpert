/**
 *  giga galerie angular app
 */

var app = angular.module("galerieApp", ["ui.bootstrap", "ngFileUpload", "ngCookies", "bootstrapLightbox", "ngSanitize", "angular.filter"]);

app.constant("serverUrl", "http://localhost:8080/gigaMvcGalerie")
    .constant("imageUrl", "/rest/images")
    .constant("licenseUrl", "/rest/licenses")
    .constant("assetSourceUrl", "/rest/assetsources")
    .constant("tagUrl", "/rest/tags");

// cette fonction est appelée au démarrage de l'app angular
// pour configurer les services utilisés
app.config(function (imageServiceProvider,
    tagServiceProvider,
    licenseAndSourceServiceProvider,
    serverUrl,
    imageUrl,
    licenseUrl,
    assetSourceUrl,
    tagUrl) {


    imageServiceProvider.setServiceUrl(serverUrl + imageUrl);
    licenseAndSourceServiceProvider.setLicenseServiceUrl(serverUrl + licenseUrl);
    licenseAndSourceServiceProvider.setAssetSourceServiceUrl(serverUrl + assetSourceUrl);
    tagServiceProvider.setServiceUrl(serverUrl + tagUrl);
});
