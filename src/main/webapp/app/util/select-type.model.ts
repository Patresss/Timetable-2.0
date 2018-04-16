export class SelectType {

    public static MAX_INT_JAVA = 2147483647;

    constructor(public id: number,
                public itemName: String,
                public itemTranslate: String,
                public value: any = null,
                public itemObject: any = null,
                public preferenceHierarchy = null) {
    }
}
