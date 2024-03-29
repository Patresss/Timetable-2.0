import {Injectable} from '@angular/core';
import {JhiDateUtils} from 'ng-jhipster';

import {ResponseWrapper} from '../../shared';
import {Http, Response} from '@angular/http';
import {Time} from '../../util/time.model';
import {EntityService} from '../entity.service';
import {Curriculum} from './curriculum.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class CurriculumService extends DivisionOwnerEntityService<Curriculum> {

    constructor(http: Http, private dateUtils: JhiDateUtils) {
        super(http, 'curriculums');
    }

    convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    convertFromServer(entity: any) {
        entity.date = this.dateUtils.convertLocalDateFromServer(entity.date)
        if (entity.startTimeString) {
            entity.startTime = new Time(entity.startTimeString);
        }
        if (entity.endTimeString) {
            entity.endTime = new Time(entity.endTimeString);
        }
    }

}
