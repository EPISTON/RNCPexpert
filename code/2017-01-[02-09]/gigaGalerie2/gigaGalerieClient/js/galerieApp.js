/**
 *  giga galerie angular app
 */

var app = angular.module("galerieApp",
                 ["ui.bootstrap",
                  "ngFileUpload",
                  "ngCookies",
                  "bootstrapLightbox",
                  "ngSanitize",
                  "angular.filter",
                  "ngRoute"]);

app.constant("serverUrl", "http://localhost:8080/gigaMvcGalerie")
    .constant("imageUrl", "/rest/images")
    .constant("licenseUrl", "/rest/licenses")
    .constant("assetSourceUrl", "/rest/assetsources")
    .constant("tagUrl", "/rest/tags");

// cette fonction est appelée au démarrage de l'app angular
// pour configurer les services utilisés
app.config(function (
    $httpProvider,
    $routeProvider,         // injection pour configuration des routes
    imageServiceProvider,
    tagServiceProvider,
    licenseAndSourceServiceProvider,
    serverUrl,
    imageUrl,
    licenseUrl,
    assetSourceUrl,
    tagUrl) {

    $routeProvider.when("/login" , {
        templateUrl: "views/loginPanel.html"
    });
    $routeProvider.when("/images" , {
        templateUrl: "views/imagePanel.html"
    });
    $routeProvider.otherwise({
        templateUrl: "views/loginPanel.html"
    });


    imageServiceProvider.setServiceUrl(serverUrl + imageUrl);
    licenseAndSourceServiceProvider.setLicenseServiceUrl(serverUrl + licenseUrl);
    licenseAndSourceServiceProvider.setAssetSourceServiceUrl(serverUrl + assetSourceUrl);
    tagServiceProvider.setServiceUrl(serverUrl + tagUrl);

    // dans toutes les requette envoyées avec $http
    // ajouter cet header qui empechera l'utilisation, entre autre
    // des formulaires d'authentification automatiques (on est en ajax)
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
    $httpProvider.defaults.xsrfCookieName = 'X-XSRF-TOKEN';
    // transmettre les infos d'authentification avec la requette
    $httpProvider.defaults.withCredentials = true;
});
