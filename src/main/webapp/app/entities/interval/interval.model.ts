import { BaseEntity } from './../../shared';

export class Interval implements BaseEntity {
    constructor(
        public id?: number,
        public includedState?: boolean,
        public startDate?: any,
        public endDate?: any,
        public periodId?: number,
    ) {
        this.includedState = false;
    }
}
