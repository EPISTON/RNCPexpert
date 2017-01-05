angular.module("galerieApp")
    .provider("licenseAndSourceService", function () {

        var licenseServiceUrl = "http://localhost";
        var AssetSourceServiceUrl = "http://localhost";

        this.setLicenseServiceUrl = function (url) {
            licenseServiceUrl = url;
        };
        this.setAssetSourceServiceUrl = function (url) {
            AssetSourceServiceUrl = url;
        };

        // cette fonction doit renvoyer le service
        // correctment configuré
        this.$get = function ($rootScope, $http, $cookies) {
            return {

                "listeLicense": function (pageNo, pageSize) {
                    pageNo = pageNo || 0;
                    pageSize = pageSize || 12;
                    return $http.get(licenseServiceUrl + "?pageNo=" + pageNo + "&pageSize=" + pageSize);
                },
                "listeAssetSource": function (pageNo, pageSize) {
                    pageNo = pageNo || 0;
                    pageSize = pageSize || 12;
                    return $http.get(AssetSourceServiceUrl + "?pageNo=" + pageNo + "&pageSize=" + pageSize);
                }

            };
        }

    })