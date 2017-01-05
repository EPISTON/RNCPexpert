angular.module("galerieApp")
    .controller("imageRootController", ['$scope', 'imageService', '$filter', function ($scope, imageService, $filter) {

        var imageMainPanels = ["views/images/images.html",
            "views/images/upload.html",
            "views/images/staging.html",
            "views/images/edit.html"];
        $scope.currentMainPanelIndex = 0;
        var ImagePanelsState = [null, null, null, null];

        $scope.galleryMode = "all";
        $scope.mainImagePanel = imageMainPanels[0];
        $scope.secondNavigation = "views/images/tagNav.html";
        $scope.currentEditId = 0;
        $scope.previousPanels = [0];


        $scope.goBackImageNav = function () {
            if ($scope.previousPanels.length > 0) {
                $scope.currentMainPanelIndex = $scope.previousPanels.pop();
                $scope.mainImagePanel = imageMainPanels[$scope.currentMainPanelIndex];
                if ($scope.currentMainPanelIndex == 0) { $scope.galleryMode = "all"; }
                if ($scope.currentMainPanelIndex == 2) { $scope.galleryMode = "staged"; }
            }
        };
        $scope.setImageNav = function (index) {
            $scope.previousPanels.push($scope.currentMainPanelIndex);
            $scope.currentMainPanelIndex = index;
            $scope.mainImagePanel = imageMainPanels[index];
            if ($scope.currentMainPanelIndex == 0) { $scope.galleryMode = "all"; }
            if ($scope.currentMainPanelIndex == 2) { $scope.galleryMode = "staged"; }
        };

        $scope.isImageNav = function (index) {
            return index == $scope.currentMainPanelIndex;
        };

        $scope.setPanelState = function (index, state) {
            return ImagePanelsState[index] = state;
        };
        $scope.getPanelState = function (index, defaultState) {
            return ImagePanelsState[index] || defaultState;
        };


        $scope.editImage = function (id, withUnstaging) {
            $scope.setImageNav(3);
            $scope.currentEditId = id;
            $scope.currentEditPoposeUnstaging = withUnstaging;
        };

        $scope.getImgDetail = function (image) {
            var content = "<h4>" + $filter("truncate")(image.name, 20, '&hellip;') + "</h4>"
                + "<p> filesize: " + $filter("byteFmt")(image.fileSize, 2) + "<br />"
                + " upload date: " + image.dateAdded + "<br />"
            if (image.tags) {
                content += "tags : ";
                var pos = 0;
                for (var i = 0; i < image.tags.length; i++) {
                    if (!(image.tags[i].systemTag)) {
                        if (pos > 0) {
                            content += ", ";
                        }
                        content += image.tags[i].libelle;
                        pos++;
                    }
                }
            }
            return content + "</p>";
        };
        $scope.getImgLink = function (id) {
            return imageService.getImgLink(id);
        };

        $scope.getThumbImgLink = function (id) {
            return imageService.getThumbImgLink(id);
        };

    }])
    .controller("imageListController", ['$scope', '$filter', 'imageService', 'tagService', 'Lightbox', '$sanitize', '$uibModal',
        function ($scope, $filter, imageService, tagService, Lightbox, $sanitize, $uibModal) {

            var panelNumber = $scope.currentMainPanelIndex;
            $scope.galleryPanelState = $scope.getPanelState(panelNumber,
                { "currentPage": 1, "totalItems": 0, "pageSize": 12,
                 "maxPaginationSize": 10, "selectedTags": [], "selectedImages": [],
                 "currentSortProperty" : "name", currentSortDirection : "asc",
                  "navState": null });


            $scope.images = [];
            $scope.gallerieUrls = [];
            var listServiceFct = null;
            if ($scope.galleryMode == "all") {
                listServiceFct = imageService.filteredList;
            }
            else {
                listServiceFct = imageService.filteredStagedList;
            }

            $scope.changeSelection = function (img) {
                var idx = -1;
                for (var i = 0; i < $scope.galleryPanelState.selectedImages.length; i++) {
                    if (img.id == $scope.galleryPanelState.selectedImages[i].id) {
                        idx = i;
                        break;
                    }
                }
                if (idx == -1) {
                    $scope.galleryPanelState.selectedImages.push(img);
                }
                else {
                    $scope.galleryPanelState.selectedImages.splice(idx, 1);
                }
            };
            $scope.isImageSelected = function (img) {
                for (var i = 0; i < $scope.galleryPanelState.selectedImages.length; i++) {
                    if (img.id == $scope.galleryPanelState.selectedImages[i].id) {
                        return true;
                    }
                }
                return false;
            };

            $scope.changeSort = function(propertyName) {
                if (propertyName === $scope.galleryPanelState.currentSortProperty) {
                    if ($scope.galleryPanelState.currentSortDirection == "asc" ) {
                        $scope.galleryPanelState.currentSortDirection = "desc";
                    }
                    else {
                        $scope.galleryPanelState.currentSortDirection = "asc";
                    }
                }
                else {
                    $scope.galleryPanelState.currentSortProperty = propertyName;
                    $scope.galleryPanelState.currentSortDirection = "asc";
                }
                $scope.refreshList();
            }
            // rafraichit le contenu de la liste des images a afficher
            $scope.refreshList = function () {
                var ids = [];
                // recuperation des tag pour filtrer
                for (var i = 0; i < $scope.galleryPanelState.selectedTags.length; i++) {
                    ids.push($scope.galleryPanelState.selectedTags[i].id);
                }
                // j'appele le service imageService pour recupere le liste des images
                listServiceFct.call(imageService, ids,
                                     $scope.galleryPanelState.currentPage - 1,
                                      $scope.galleryPanelState.pageSize,
                                      $scope.galleryPanelState.currentSortProperty,
                                      $scope.galleryPanelState.currentSortDirection)
                              .then(function (response) {
                    $scope.images = response.data.content;
                    $scope.galleryPanelState.totalItems = response.data.totalElements;
                    $scope.gallerieUrls = [];
                    var total = $scope.images.length;
                    for (var i = 0; i < total; i++) {
                        var caption = $scope.images[i].name + " (" + (i + 1) + "/" + total + ")";
                        $scope.gallerieUrls.push({ "url": $scope.getImgLink($scope.images[i].id), "caption": caption });
                    }
                }, function (response) {
                    $scope.images = [];
                });
            };



            $scope.resize = function (size) {
                var newPageSize = 0;
                if (size === "xs") { newPageSize = 6; }
                else if (size === "sm") { newPageSize = 8; }
                else if (size === "md") { newPageSize = 12; }
                else if (size === "lg") { newPageSize = 16; }
                else { newPageSize = 24; }

                if ($scope.galleryPanelState.pageSize !== newPageSize) {
                    console.log("size change from " + $scope.galleryPanelState.pageSize + " to " + newPageSize);
                    var newPage = Math.floor(($scope.galleryPanelState.currentPage - 1) * $scope.galleryPanelState.pageSize / newPageSize) + 1;
                    $scope.galleryPanelState.pageSize = newPageSize;
                    $scope.galleryPanelState.currentPage = newPage;

                    $scope.refreshList();
                }
            };



            $scope.$on('tagService:selectedTagRefreshed', function (evt, data) {
                $scope.galleryPanelState.selectedTags = data.tags;
                $scope.refreshList();
            });
            $scope.$on('tagService:tagChoiceRefreshed', function (evt, data) {
                $scope.galleryPanelState.currentTagSearch = data.currentSearch;
                $scope.galleryPanelState.currentTagAvailability = data.currentAvailability;
            });
            $scope.clearSelection = function () {
                $scope.galleryPanelState.selectedImages = [];
            };
            $scope.addVisibleToSelection = function () {
                for (var i = 0; i < $scope.images.length; i++) {
                    if (!($scope.isImageSelected($scope.images[i]))) {
                        $scope.galleryPanelState.selectedImages.push($scope.images[i]);
                    }
                }
            };
            $scope.unstageSelectedAssets = function () {
                var message = "unstaging images "
                    + $filter("map")($scope.galleryPanelState.selectedImages, function (elem) { return elem.name; })
                        .join(", ");
                $uibModal.open({
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'views/dialogs/confirmDialog.html',
                    controller: 'confirmModalController',
                    controllerAs: '$ctrl',
                    resolve: {
                        modalConfig: function () {
                            return {
                                "title": "unstage assets",
                                "message": message,
                                "confirmLabel": "unstage All"
                            };
                        }
                    }
                }).result.then(function (infos) {
                    console.log("unstage confirmed");
                    var ids = $filter("map")($scope.galleryPanelState.selectedImages, function (elem) { return elem.id; })
                    tagService.multiAssetUnstage(ids)
                        .then(function (response) {
                            console.log(response.data.unstagedCount + " images unstaged");
                            $scope.galleryPanelState.selectedImages = [];
                            $scope.refreshList();
                        },
                        function (response) {
                            console.log("could not unstage images");
                        });
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            };
            $scope.removeTagFromAssets = function () {
                var message = "removing tag " + $scope.galleryPanelState.currentTagSearch + " from images "
                    + $filter("map")($scope.galleryPanelState.selectedImages, function (elem) { return elem.name; })
                        .join(", ");
                $uibModal.open({
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'views/dialogs/confirmDialog.html',
                    controller: 'confirmModalController',
                    controllerAs: '$ctrl',
                    resolve: {
                        modalConfig: function () {
                            return {
                                "title": "remove tag from assets",
                                "message": message,
                                "confirmLabel": "remove tag",
                                "confirmClass": "btn-danger"
                            };
                        }
                    }
                }).result.then(function (modalConfig) {
                    console.log("remove tag confirmed");
                    var ids = $filter("map")($scope.galleryPanelState.selectedImages, function (elem) { return elem.id; });
                    tagService.multiAssetRemoveTag($scope.galleryPanelState.currentTagAvailability.id, ids)
                        .then(function (response) {
                            console.log("tag removed");
                            $scope.refreshList();
                        },
                        function (response) {
                            console.log("could not remove tag");
                        });
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            };
            $scope.addTagToAssets = function () {
                var message = "adding tag " + $scope.galleryPanelState.currentTagSearch + " to images "
                    + $filter("map")($scope.galleryPanelState.selectedImages, function (elem) { return elem.name; })
                        .join(", ");
                $uibModal.open({
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'views/dialogs/confirmDialog.html',
                    controller: 'confirmModalController',
                    controllerAs: '$ctrl',
                    resolve: {
                        modalConfig: function () {
                            return {
                                "title": "add tag to assets",
                                "message": message,
                                "confirmLabel": "add tag",
                                "confirmClass": "btn-primary"
                            };
                        }
                    }
                }).result.then(function (modalConfig) {
                    console.log("remove tag confirmed");
                    var ids = $filter("map")($scope.galleryPanelState.selectedImages, function (elem) { return elem.id; });
                    tagService.multiAssetAddTag($scope.galleryPanelState.currentTagAvailability.id, ids)
                        .then(function (response) {
                            console.log("tag added");
                            $scope.refreshList();
                        },
                        function (response) {
                            console.log("could not add tag");
                        });
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            };
            $scope.openSelectedModalGallerie = function () {
                var selectedGallerieUrls = [];
                var total = $scope.galleryPanelState.selectedImages.length;
                for (var i = 0; i < total; i++) {
                    var caption = $scope.galleryPanelState.selectedImages[i].name + " (" + (i + 1) + "/" + total + ")";
                    selectedGallerieUrls.push({ "url": $scope.getImgLink($scope.galleryPanelState.selectedImages[i].id), "caption": caption });
                }
                Lightbox.openModal(selectedGallerieUrls, 0);
            };
            $scope.openModalGallerie = function (index) {
                Lightbox.openModal($scope.gallerieUrls, index);
            };
            $scope.deleteImage = function (removedImage) {
                $scope.deleteImages([removedImage], false);
            };
            $scope.deleteImages = function (removedImages, clearSelectionAfter) {
                var message = "deleting images "
                    + $filter("map")(removedImages, function (elem) { return elem.name; })
                        .join(", ");
                $uibModal.open({
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'views/dialogs/confirmDialog.html',
                    controller: 'confirmModalController',
                    controllerAs: '$ctrl',
                    resolve: {
                        modalConfig: function () {
                            return {
                                "title": "delete images",
                                "message": message,
                                "confirmLabel": "delete images",
                                "confirmClass": "btn-danger"
                            };
                        }
                    }
                }).result.then(function (modalConfig) {
                    console.log("remove images confirmed");
                    var ids = $filter("map")(removedImages, function (elem) { return elem.id; });
                    imageService.removeImages(ids)
                        .then(function (response) {
                            console.log("images removed");
                            if (clearSelectionAfter) {
                                $scope.galleryPanelState.selectedImages = [];
                            }
                            $scope.refreshList();
                        },
                        function (response) {
                            console.log("could not remove images");
                        });
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            };

            $scope.$on('$destroy', function () {
                $scope.galleryPanelState.navState = tagService.getNavState();
                $scope.setPanelState(panelNumber, $scope.galleryPanelState);
            });

            tagService.setAssetId(0);
            tagService.setTagMode("filter");
            tagService.setNavState($scope.galleryPanelState.navState);
            tagService.refreshAll();
            tagService.refreshSelected();

        }])
    .controller("confirmModalController", function ($uibModalInstance, modalConfig) {
        var $ctrl = this;
        $ctrl.modalConfig = {};
        console.log(modalConfig);
        $ctrl.modalConfig.title = modalConfig.title || "please confirm";
        $ctrl.modalConfig.message = modalConfig.message || "confirming will execute this action";
        $ctrl.modalConfig.cancelClass = modalConfig.cancelClass || "btn-warning";
        $ctrl.modalConfig.confirmClass = modalConfig.confirmClass || "btn-success";
        $ctrl.modalConfig.cancelLabel = modalConfig.cancelLabel || "Cancel";
        $ctrl.modalConfig.confirmLabel = modalConfig.confirmLabel || "Confirm";

        $ctrl.confirm = function () {
            $uibModalInstance.close(modalConfig);
        };
        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    })
    .controller("imageTagModalController", function ($uibModalInstance, imagesToTag, tagInfo) {
        var $ctrl = this;
        $ctrl.imagesToTag = imagesToTag;
        $ctrl.tagInfo = tagInfo;

        $ctrl.addTag = function () {
            $uibModalInstance.close({ "imagesToTag": $ctrl.imagesToTag, "tagInfo": $ctrl.tagInfo });
        };
        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    })
    .controller("imageRemoveModalController", function ($uibModalInstance, removedImages) {
        var $ctrl = this;
        $ctrl.removedImages = removedImages;

        $ctrl.remove = function () {
            $uibModalInstance.close($ctrl.removedImages);
        };
        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    })
    .controller("imageUploadController", ['$scope', 'imageService', 'licenseAndSourceService', 'tagService',
        function ($scope, imageService, licenseAndSourceService, tagService) {
            $scope.files = [];
            $scope.currentLicense = null;
            $scope.currentAssetSource = null;

            $scope.uploadPanelState = $scope.getPanelState(1,
                { "selectedTags": [], "navState": null });
            tagService.setAssetId(0);
            tagService.setTagMode("filter");
            tagService.setNavState($scope.uploadPanelState.navState);

            $scope.$watch('files', function () {
                var files = $scope.files;
                if (angular.isArray(files) && files.length >= 0) {
                    for (var i = 0; i < files.length; i++) {
                        imageService.upload(files[i], $scope.currentLicense, $scope.currentAssetSource, $scope.uploadPanelState.selectedTags);
                    }
                }
            });
            $scope.log = '';
            $scope.fileProgressBars = {};
            $scope.uploadAlerts = [];
            $scope.closeAlertUpload = function (index) {
                $scope.uploadAlerts.splice(index, 1);
            };
            $scope.$on('imageUpload:Progress', function (event, data) {
                $scope.fileProgressBars[data.filename] = data.Progress;
            });
            $scope.$on('imageUpload:Complete', function (event, data) {
                delete $scope.fileProgressBars[data.filename];
                var alertUpload = { type: "success" };
                alertUpload.message = "successfully uploaded " + data.filename;
                $scope.uploadAlerts.push(alertUpload);
            });
            $scope.$on('tagService:selectedTagRefreshed', function (evt, data) {
                $scope.uploadPanelState.selectedTags = data.tags;
            });


            licenseAndSourceService.listeLicense(0, 50)
                .then(function (response) {
                    $scope.listeLicense = response.data.content;
                    $scope.currentLicense = $scope.listeLicense[0];
                },
                function (response) {
                    console.log("could not find License list");
                });

            licenseAndSourceService.listeAssetSource(0, 50)
                .then(function (response) {
                    $scope.listeAssetSource = response.data.content;
                    $scope.currentAssetSource = $scope.listeAssetSource[0];
                },
                function (response) {
                    console.log("could not find AssetSource list");
                });

            $scope.selectLicense = function (license) {
                $scope.currentLicense = license;
            };
            $scope.selectSource = function (AssetSource) {
                $scope.currentAssetSource = AssetSource;
            };
            $scope.$on('$destroy', function () {
                $scope.uploadPanelState.navState = tagService.getNavState();
                $scope.setPanelState(1, $scope.uploadPanelState);
            });

            tagService.refreshAll();
            tagService.refreshSelected();

        }])
    .controller("imageEditController", ['$scope', 'imageService', 'licenseAndSourceService', 'tagService',
        function ($scope, imageService, licenseAndSourceService, tagService) {

            $scope.editimg = null;
            $scope.listeLicense = [];
            $scope.listeAssetSource = [];
            tagService.setTagMode("edit");

            imageService.findOne($scope.currentEditId)
                .then(function (response) {
                    $scope.editimg = response.data;
                    tagService.setAssetId($scope.editimg.id);
                    tagService.refreshSelected();
                },
                function (response) {
                    console.log("could not find image to edit or error");
                });
            licenseAndSourceService.listeLicense(0, 50)
                .then(function (response) {
                    $scope.listeLicense = response.data.content;
                },
                function (response) {
                    console.log("could not find License list");
                });

            licenseAndSourceService.listeAssetSource(0, 50)
                .then(function (response) {
                    $scope.listeAssetSource = response.data.content;
                },
                function (response) {
                    console.log("could not find AssetSource list");
                });

            $scope.selectLicense = function (license) {
                $scope.editimg.license = license;
            };
            $scope.selectSource = function (AssetSource) {
                $scope.editimg.source = AssetSource;
            };
            $scope.saveUnstageImg = function () {
                imageService.saveOneAndUnstage($scope.editimg, $scope.editimg.license.id, $scope.editimg.source.id)
                    .then(function (response) {
                        $scope.goBackImageNav();
                    },
                    function (response) {
                        console.log("error trying to save image");
                        console.log(response);
                        $scope.goBackImageNav();
                    });

            };
            $scope.saveImg = function () {
                imageService.saveOne($scope.editimg, $scope.editimg.license.id, $scope.editimg.source.id)
                    .then(function (response) {
                        $scope.goBackImageNav();
                    },
                    function (response) {
                        console.log("error trying to save image");
                        console.log(response);
                        $scope.goBackImageNav();
                    });

            };
            $scope.cancelEdit = function () {
                $scope.goBackImageNav();
            };

        }]);
