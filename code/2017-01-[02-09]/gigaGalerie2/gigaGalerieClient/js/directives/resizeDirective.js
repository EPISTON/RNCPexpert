angular.module("galerieApp")
    .directive('btResize', ['$window', function ($window) {
        return {
            link: link,
            restrict: 'A'
        };

        function link(scope, element, attrs) {
            var width = -1;
            var currentBt = "";
            var callback = attrs.btResize;

            function onResize() {
                var newWidth = window.innerWidth;
                if (width !== newWidth) {
                    width = newWidth;
                    var newBt = getBootstrapSize(width);
                    if (newBt !== currentBt) {
                        console.log("resize " + currentBt + " to " + newBt + " size : " + width);
                        currentBt = newBt;
                        scope[callback](currentBt);
                    }
                }
            };

            function getBootstrapSize(size) {
                if (size < 768) { return "xs"; }
                else if (size < 992) { return "sm"; }
                else if (size < 1200) { return "md"; }
                else if (size < 1600) { return "lg"; }
                else { return "xl"; }
            };

            function cleanUp() {
                angular.element($window).off('resize', onResize);
            };

            angular.element($window).on('resize', onResize);
            scope.$on('$destroy', cleanUp);

            onResize();
        }

    }]);