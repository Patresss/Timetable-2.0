export class ListItem {
    id: Number;
    itemName: String;
    itemTranslate: String;
    value: any = null;
    itemObject: any = null;
    preferenceHierarchy = null;
}

export class MyException {
    status: number;
    body: any;

    constructor(status: number, body: any) {
        this.status = status;
        this.body = body;
    }

}
