export class SelectUtil {

    public static entityListToSelectList(entityList: any[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.name, item: entity};
            selectList.push(obj)
        });
        return selectList;
    }

}
