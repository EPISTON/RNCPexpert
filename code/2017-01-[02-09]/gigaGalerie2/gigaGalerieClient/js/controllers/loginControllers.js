angular.module("galerieApp")
    .controller("loginController", ['$rootScope', '$scope', '$http', '$location', 'serverUrl',
                     function ($rootScope, $scope, $http, $location, serverUrl) {
        $scope.credentials = {};

        $scope.authenticate = function(credentials, callback) {
            var headers = credentials ? {
                authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
            } : {};
            $http.get(serverUrl +'/user', { "headers" : headers, withCredentials: true})
                    .then(function(response) {
                        // en cas de success
                        if (response.data.name) {
                            $rootScope.authenticated = true;
                        }
                        else {
                            $rootScope.authenticated = false;
                        }
                        callback && callback();
                    }, function (response) {
                        $rootScope.authenticated = false;
                        callback && callback();
                    });
        };

        $scope.login = function() {
            $scope.authenticate($scope.credentials, function() {
                if ($rootScope.authenticated) {
                    $location.path("/images");
                }
                else {
                    console.log("erreur au login");
                }
            });
        };
    }]);
