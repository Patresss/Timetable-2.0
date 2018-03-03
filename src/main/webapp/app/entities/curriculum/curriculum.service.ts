import {Injectable} from '@angular/core';
import {JhiDateUtils} from 'ng-jhipster';

import {Curriculum} from './curriculum.model';

@Injectable()
export class CurriculumService {

    constructor(private dateUtils: JhiDateUtils) {
    }

    public convertItemFromServer(entity: Curriculum) {
    }

    public convert(curriculum: Curriculum): Curriculum {
        const copy: Curriculum = Object.assign({}, curriculum);
        return copy;
    }
}
