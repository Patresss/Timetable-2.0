import {PreferenceHierarchy} from '../preference/preferecne-hierarchy.model';

export class SelectUtil {

    public static entityListToSelectList(entityList: any[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.name,  itemTranslate: '', item: entity, preferenceHierarchy: new PreferenceHierarchy()};
            selectList.push(obj)
        });
        return selectList;
    }

    public static teacherListToSelectList(entityList: any[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.fullName, item: entity, itemTranslate: '', preferenceHierarchy: new PreferenceHierarchy()};
            selectList.push(obj)
        });
        return selectList;
    }

}
