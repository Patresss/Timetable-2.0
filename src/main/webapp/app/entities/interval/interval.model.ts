import { BaseEntity } from './../../shared';

export class Interval implements BaseEntity {
    constructor(
        public id?: number,
        public included?: boolean,
        public startDate?: any,
        public endDate?: any,
        public periodId?: number,
    ) {
        this.included = false;
    }
}
