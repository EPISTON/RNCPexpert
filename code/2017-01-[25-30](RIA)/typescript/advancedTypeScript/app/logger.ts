

// target -> le protype de l'element visé
// propertyKey -> nom de la methode
// descriptor -> informations sur ce qui est décoré
function logger(target: Object,
                propertyKey: string,
                descriptor: TypedPropertyDescriptor<any>) {
    let methodeOriginale = descriptor.value; // methode d'origine
    descriptor.value = function(...args: any[]) {
        console.log("argument appel: " + JSON.stringify(args));

        let result = methodeOriginale.apply(this, args); // appel de l'original

        console.log("return value : " + result);
        return result;
    };

    return descriptor;
}

export {logger};
