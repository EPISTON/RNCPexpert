angular.module("galerieApp")
    .provider("tagService", function () {

        var serviceUrl = "http://localhost";


        this.setServiceUrl = function (url) {
            serviceUrl = url;
        };



        this.$get = function ($rootScope, $http, $cookies, $q) {
            var tagMode = "filter"; // "filter" or "edit"
            var currentPageNo = 1;
            var currentPageSize = 15;
            var currentTotalItems = 0;
            var currentAssetId = 0; // for Asset edit mode
            var currentSelectedTags = [];
            var currentSearch = "";
            var currentTagPage = [];
            var defaultAvailability = { "exists": true, "editable": false };
            var currentAvailability = defaultAvailability;

            return {
                "setNavState": function (navState) {
                    if (navState) {
                        currentPageNo = navState.currentPageNo;
                        currentPageSize = navState.currentPageSize;
                        currentTotalItems = navState.currentTotalItems;
                        currentSearch = navState.currentSearch;
                        currentSelectedTags = navState.currentSelectedTags;
                    }
                },
                "getNavState": function () {
                    return {
                        "currentPageNo": currentPageNo,
                        "currentPageSize": currentPageSize,
                        "currentTotalItems": currentTotalItems,
                        "currentSearch": currentSearch,
                        "currentSelectedTags": currentSelectedTags,
                    };
                },
                "setTagMode": function (newTagMode) {
                    tagMode = newTagMode;
                    currentSelectedTags = [];
                },
                "setPageNo": function (noPage) { currentPageNo = noPage; },
                "getTotalItems": function () { return currentTotalItems; },
                "getPageSize": function () { return currentPageSize; },
                "getPageNo": function () { return currentPageNo; },
                "getCurrentSearch": function () { return currentSearch; },
                "getCurrentAvailability": function () { return currentAvailability; },
                "setCurrentSearch": function (search) { currentSearch = search; },
                "setAssetId": function (assetId) {
                    currentAssetId = assetId;
                },
                "checkAvailability": function (libelle) {
                    var deferred = $q.defer();

                    libelle = libelle.trim();
                    if (libelle.length > 1) {
                        url = serviceUrl + '/available/' + libelle.trim();
                        $http.get(url)
                            .then(function (response) {
                                currentAvailability = response.data;
                                deferred.resolve(currentAvailability);
                            }, function (response) {
                                console.log("erreur a la requette de demande d'existence de tag");
                                currentAvailability = defaultAvailability;
                                deferred.resolve(currentAvailability);
                            });
                    }
                    else {
                        currentAvailability = defaultAvailability;
                        deferred.resolve(currentAvailability);
                    }
                    return deferred.promise;
                },

                "findOne": function (id) {
                    return $http.get(serviceUrl + "/" + id);
                },
                "saveOne": function (tag) {
                    return $http.post(serviceUrl, tag);
                },
                "multiAssetAddTag": function (tagId, assetsId) {
                    return $http.post(serviceUrl + "/add/" + assetsId.join() + "/" + tagId, {})
                },
                "multiAssetRemoveTag": function (tagId, assetsId) {
                    return $http.post(serviceUrl + "/remove/" + assetsId.join() + "/" + tagId, {})
                },
                "multiAssetUnstage": function (assetsId) {
                    return $http.post(serviceUrl + "/unstage/" + assetsId.join(), {})
                },
                "addTag": function (tag) {
                    var self = this;
                    if (tagMode === "edit" && currentAssetId !== 0) {
                        $http.post(serviceUrl + "/add/" + currentAssetId + "/" + tag.id, {})
                            .then(function (response) {
                                $rootScope.$broadcast("tagService:tagAdded", { 'tag': response.data });
                                self.refreshSelected();
                            },
                            function (response) {
                                console.log("could not add tag, error " + response);
                            });
                    }
                    else if (tagMode === "filter") {
                        for (var i = 0; i < currentSelectedTags.length; i++) {
                            if (currentSelectedTags[i].id == tag.id) {
                                console.log('tag is already selected');
                                return;
                            }
                        }
                        currentSelectedTags.push(tag);
                        $rootScope.$broadcast("tagService:tagAdded", { 'tag': tag });
                        $rootScope.$broadcast("tagService:selectedTagRefreshed", { tags: currentSelectedTags });
                    }
                },
                "removeTag": function (tag) {
                    var self = this;
                    if (tagMode === "edit" && currentAssetId !== 0) {
                        $http.post(serviceUrl + "/remove/" + currentAssetId + "/" + tag.id, {})
                            .then(function (response) {
                                $rootScope.$broadcast("tagService:tagRemoved", { 'tag': response.data });
                                self.refreshSelected();
                            },
                            function (response) {
                                console.log("could not remove tag, error " + response);
                            });
                    }
                    else if (tagMode === "filter") {
                        var indexToRemove = -1;
                        for (var i = 0; i < currentSelectedTags.length; i++) {
                            if (currentSelectedTags[i].id == tag.id) {
                                indexToRemove = i;
                                break;
                            }
                        }
                        if (indexToRemove !== -1) {
                            currentSelectedTags.splice(indexToRemove, 1);
                            $rootScope.$broadcast("tagService:tagRemoved", { 'tag': tag });
                            $rootScope.$broadcast("tagService:selectedTagRefreshed", { tags: currentSelectedTags });
                        }
                        else {
                            console.log('remove failed, tag is not selected');
                        }
                    }
                },

                "refreshAll": function () {
                    this.searchAll(currentPageNo - 1, currentPageSize, currentSearch)
                        .then(function (response) {
                            currentTotalItems = response.data.totalElements;
                            currentPageNo = response.data.number + 1;
                            currentPageSize = response.data.size;
                            currentTagPage = response.data.content;
                            // on prévient qu'il faut rafraichir la page
                            $rootScope.$broadcast("tagService:tagChoiceRefreshed", {
                                'tags': currentTagPage,
                                'totalItems': currentTotalItems,
                                'pageSize': currentPageSize,
                                'pageNo': currentPageNo,
                                'currentSearch': currentSearch,
                                'currentAvailability': currentAvailability
                            });
                        },
                        function (response) {
                            console.log("could not query tags, error " + response);
                        });
                },
                "refreshSelected": function () {
                    if (tagMode === "edit" && currentAssetId !== 0) {
                        this.listAllTagging(0, 50, currentAssetId)
                            .then(function (response) {
                                currentSelectedTags = response.data.content;
                                $rootScope.$broadcast("tagService:selectedTagRefreshed", { tags: currentSelectedTags });
                            },
                            function (response) {
                                console.log('could not get associated tags: ' + response);
                            });
                    }
                    else {
                        $rootScope.$broadcast("tagService:selectedTagRefreshed", { tags: currentSelectedTags });
                    }
                },


                "searchAll": function (pageNo, pageSize, searchTerm) {
                    pageNo = pageNo || 0;
                    pageSize = pageSize || 12;
                    var url = serviceUrl;
                    searchTerm = searchTerm.trim();
                    if (searchTerm.length > 0) {
                        url += '/search/' + searchTerm.trim();
                    }
                    return $http.get(url + "?pageNo=" + pageNo + "&pageSize=" + pageSize);
                },
                "listAllTagging": function (pageNo, pageSize, assetId) {
                    pageNo = pageNo || 0;
                    pageSize = pageSize || 12;
                    var url = serviceUrl;
                    if (assetId !== 0) {
                        url += "/findRelated/" + assetId;
                    }
                    return $http.get(url + "?pageNo=" + pageNo + "&pageSize=" + pageSize);
                }



            };
        }

    })