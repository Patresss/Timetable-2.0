import {Injectable} from '@angular/core';
import {JhiDateUtils} from 'ng-jhipster';

import {Interval} from './interval.model';

@Injectable()
export class IntervalService {

    constructor(private dateUtils: JhiDateUtils) {
    }

    public convertItemFromServer(entity: Interval) {
        entity.startDate = this.dateUtils.convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils.convertLocalDateFromServer(entity.endDate);
    }

    public convert(interval: Interval): Interval {
        const copy: Interval = Object.assign({}, interval);
        copy.startDate = this.dateUtils.convertLocalDateToServer(interval.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(interval.endDate);
        return copy;
    }
}
