define(["require", "exports"], function (require, exports) {
    "use strict";
    // target -> le protype de l'element visé
    // propertyKey -> nom de la methode
    // descriptor -> informations sur ce qui est décoré
    function logger(target, propertyKey, descriptor) {
        var methodeOriginale = descriptor.value; // methode d'origine
        descriptor.value = function () {
            var args = [];
            for (var _i = 0; _i < arguments.length; _i++) {
                args[_i] = arguments[_i];
            }
            console.log("argument appel: " + JSON.stringify(args));
            var result = methodeOriginale.apply(this, args); // appel de l'original
            console.log("return value : " + result);
            return result;
        };
        return descriptor;
    }
    exports.logger = logger;
});
//# sourceMappingURL=logger.js.map