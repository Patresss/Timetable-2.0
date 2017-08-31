import { BaseEntity } from './../../shared';
import {Interval} from "../interval/interval.model";

export class Period implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public intervalTimes: Interval[] = [],
        public timetables?: BaseEntity[],
        public divisionId?: number,
    ) {
    }
}
