//$uibmodal permet de generer des dialog bootstrap
angular.module("galerieApp")
    .controller("tagNavController", ['$rootScope', '$scope', 'tagService', '$uibModal', '$q', function ($rootScope, $scope, tagService, $uibModal, $q) {
        $scope.selectedTags = [];
        $scope.unselectedTags = [];
        $scope.currentAssetId = null;
        $scope.currentSearch = '';
        $scope.tagButtonClass = "btn-default disabled";
        $scope.tabButtonIcon = "glyphicon-plus";
        $scope.totalItems = tagService.getTotalItems();
        $scope.pageNo = tagService.getPageNo();
        $scope.pageSize = tagService.getPageSize();
        $scope.maxPaginationSize = 3;

        $scope.$watch('currentSearch', function (newSearch, oldSearch) {
            tagService.checkAvailability(newSearch)
                .then(function (result) {
                    if (!result.exists) {
                        $scope.tagButtonClass = "btn-success";
                        $scope.tabButtonIcon = "glyphicon-plus";
                        $scope.edittag = { id: 0 };
                    }
                    else if (result.editable) {
                        $scope.tagButtonClass = "btn-success";
                        $scope.tabButtonIcon = "glyphicon-pencil";
                        $scope.edittag = { id: result.id };
                    }
                    else {
                        $scope.tagButtonClass = "btn-default disabled";
                        $scope.tabButtonIcon = "glyphicon-remove";
                    }
                });
            tagService.setCurrentSearch(newSearch);
            tagService.refreshAll();
        });

        $scope.setSearch = function (libelle) {
            $scope.currentSearch = libelle;
        };

        $scope.$on('tagNav:setAssetId', function (evt, data) {
            $scope.currentAssetId = data.assetId;
        });

        $scope.$on('tagService:tagChoiceRefreshed', function (evt, data) {
            $scope.unselectedTags = data.tags;
            $scope.totalItems = data.totalItems;
            $scope.pageNo = data.pageNo;
            $scope.pageSize = data.pageSize;
        });

        $scope.$on('tagService:selectedTagRefreshed', function (evt, data) {
            $scope.selectedTags = data.tags;

        });

        $scope.addTag = function (tag) {
            tagService.addTag(tag);
        };
        $scope.removeTag = function (tag) {
            tagService.removeTag(tag);
        };

        $scope.edittag = { id: 0 };
        $scope.openTagModal = function () {
            var p = null;
            if ($scope.edittag.id === 0) {
                var deferred = $q.defer();
                deferred.resolve({ "data": { id: 0, "libelle": $scope.currentSearch, "description": "" } });
                p = deferred.promise;
            }
            else {
                p = tagService.findOne($scope.edittag.id);
            }
            p.then(function (response) {
                $scope.edittag = response.data;
                var modalInstance = $uibModal.open({
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'views/images/tagEditDialog.html',
                    controller: 'tagEditModalController',
                    controllerAs: '$ctrl',
                    resolve: {
                        edittag: function () {
                            return $scope.edittag;
                        }
                    }
                });
                modalInstance.result.then(function (edittag) {
                    $scope.edittag = edittag;
                    console.log("model returned ");
                    console.log(edittag);
                    tagService.saveOne($scope.edittag)
                        .then(function (response) {
                            console.log("tag saved");
                            tagService.refreshAll();
                        },
                        function (response) {
                            console.log("could not save tag");
                        });
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            },
                function (response) {
                    console.log("tag to edit not found ");
                    console.log(response);
                });
        };
        $scope.refreshAll = function () {
            tagService.setPageNo($scope.pageNo);
            tagService.refreshAll();
        };
        tagService.refreshAll();
    }])
    .controller("tagEditModalController", function ($uibModalInstance, edittag) {
        var $ctrl = this;
        $ctrl.edittag = edittag;
        if (edittag.id == 0) {
            $ctrl.currentTagName = edittag.libelle;
            $ctrl.title = "Tag Creation";
        }
        else {
            $ctrl.currentTagName = edittag.libelle;
            $ctrl.title = "Tag Edition";
        }

        $ctrl.ok = function () {
            $uibModalInstance.close($ctrl.edittag);
        };
        $ctrl.remove = function () {
            $uibModalInstance.dismiss('remove');
        };
        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
