angular.module("galerieApp")
    .provider("imageService", function () {

        var serviceUrl = "http://localhost";

        this.setServiceUrl = function (url) {
            serviceUrl = url;
        };

 
        this.$get = function ($rootScope, $http, $timeout, $cookies, Upload) {
        // fonction privé
            var generateIdsList = function(tags) {
                    var ids = "";
                    for (var i = 0; i < tags.length; i++) {
                        if (i > 0) { ids += ","; }
                        ids += (tags[i].excluded ? -tags[i].id : tags[i].id);
                    }
                    return ids;
                };
            return {
                "filteredList": function (tags, pageNo, pageSize, sortProperty, sortDirection) {
                    var url = serviceUrl;
                    if (!angular.isArray(tags) || tags.length == 0) {
                        url += "/full";
                    }                  
                    else {
                        url += "/tagSearchFull/" + generateIdsList(tags);
                    }  
                    pageNo = pageNo || 0;
                    pageSize = pageSize || 12;
                    sortProperty = sortProperty || "name";
                    sortDirection = sortDirection || "asc";
                    return $http.get(url + "?pageNo=" + pageNo + "&pageSize=" + pageSize
                                    + "&sort=" + sortProperty + "," + sortDirection);
                },
                "filteredStagedList": function (tags, pageNo, pageSize, sortProperty, sortDirection) {
                    var url = serviceUrl;
                    if (!angular.isArray(tags) || tags.length == 0) {
                        url += "/stagedfull";
                    }                  
                    else {
                        url += "/staged/tagSearchFull/" + generateIdsList(tags);
                    }  
                    pageNo = pageNo || 0;
                    pageSize = pageSize || 12;
                    sortProperty = sortProperty || "name";
                    sortDirection = sortDirection || "asc";
                    return $http.get(url + "?pageNo=" + pageNo + "&pageSize=" + pageSize
                                    + "&sort=" + sortProperty + "," + sortDirection);
                },
                "findOne": function (id) {
                    return $http.get(serviceUrl + "/" + id);
                },
                "saveOne": function (image, licenseId, sourceId) {
                    var token = $cookies.get('X-XSRF-TOKEN');
                    var url = serviceUrl + "/save/" + licenseId + "/" + sourceId;
                    return $http.post(url + "?_csrf=" + token, image);
                },
                "saveOneAndUnstage": function (image, licenseId, sourceId) {
                    var token = $cookies.get('X-XSRF-TOKEN');
                    var url = serviceUrl + "/saveunstage/" + licenseId + "/" + sourceId;
                    return $http.post(url + "?_csrf=" + token, image);
                },
                "removeImages": function (ids) {
                    var token = $cookies.get('X-XSRF-TOKEN');
                    var url = serviceUrl + "/delete/" + ids.join();
                    return $http.delete(url + "?_csrf=" + token);
                },
                "getImgLink": function (id) {
                    return serviceUrl + "/data/" + id;
                },
                "getThumbImgLink": function (id) {
                    return serviceUrl + "/thumbdata/" + id;
                },
                "upload": function (file, license, assetSource, tags) {
                    var token = $cookies.get('X-XSRF-TOKEN');
                    if (!file.$error) {
                        var p = {};
                        var uploadUrl = serviceUrl + "/data?licenseId=" + license.id
                            + "&sourceId=" + assetSource.id + "&_csrf=" + token;
                        if (tags.length > 0) {
                            uploadUrl += "&tagsId=";
                            for (var i = 0; i < tags.length; i++) {
                                if (i > 0) {
                                    uploadUrl += "," + tags[i].id;
                                }
                                else {
                                    uploadUrl += tags[i].id;
                                }
                            }
                        }
                        Upload.upload({
                            url: uploadUrl,
                            data: { file: file }
                        }).progress(function (evt) {
                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            $rootScope.$broadcast('imageUpload:Progress',
                                { "filename": evt.config.data.file.name, "progess": progressPercentage });

                        }).success(function (data, status, headers, config) {
                            $timeout(function () {
                                $rootScope.$broadcast('imageUpload:Complete', { "filename": config.data.file.name });
                            });
                        });
                    }
                }
            };
        }

    })