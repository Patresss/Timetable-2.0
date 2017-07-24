import { BaseEntity } from './../../shared';

export class Properties implements BaseEntity {
    constructor(
        public id?: number,
        public propertyKey?: string,
        public propertyValue?: string,
        public divisionId?: number,
    ) {
    }
}
