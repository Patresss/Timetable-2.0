import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {CurriculumService} from '../curriculum';
import {CurriculumList} from './curriculum-list.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class CurriculumListService extends DivisionOwnerEntityService<CurriculumList> {

    constructor(http: Http, private curriculumService: CurriculumService) {
        super(http, 'curriculum-listes')
    }

    convertEntity(jsonResponse: any) {
        if (jsonResponse.curriculumTimes != null) {
            for (let i = 0; i < jsonResponse.curriculumTimes.length; i++) {
                this.curriculumService.convertItemFromServer(jsonResponse.curriculumTimes[i]);
            }
        }
    }

    convert(curriculumList: CurriculumList): CurriculumList {
        const copy: CurriculumList = Object.assign({}, curriculumList);
        copy.curriculums = Object.assign([], curriculumList.curriculums);
        if (copy.curriculums != null) {
            for (let i = 0; i < copy.curriculums.length; i++) {
                copy.curriculums[i] = this.curriculumService.convert(copy.curriculums[i]);
            }
        }
        return copy;
    }

}
