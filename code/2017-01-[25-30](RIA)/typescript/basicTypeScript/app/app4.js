var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var Figure = (function () {
    function Figure(x, y) {
        this.x = x;
        this.y = y;
    }
    return Figure;
}());
var Triangle = (function (_super) {
    __extends(Triangle, _super);
    function Triangle(x, y, taille) {
        var _this = _super.call(this, x, y) || this;
        _this.taille = taille;
        return _this;
    }
    Triangle.prototype.afficher = function () {
        return "triangle de taille " + this.taille + " au coordonn\u00E9es " + this.x + " " + this.y;
    };
    return Triangle;
}(Figure));
var tri1 = new Triangle(10, 15, 5);
console.log(tri1.afficher());
var aff1 = tri1;
//# sourceMappingURL=app4.js.map