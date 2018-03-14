import {BaseEntity} from '../../shared';
import {Curriculum} from '../curriculum';

export class CurriculumList implements BaseEntity {
    constructor(public id?: number,
                public name?: string,
                public curriculums: Curriculum[] = [],
                public divisionOwnerId?: number,
                public startDate?: any,
                public endDate?: any,
                public periodId?: number,
                public skills = []) {
    }
}
