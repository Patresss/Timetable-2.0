import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Period} from './period.model';
import {IntervalService} from '../interval';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class PeriodService extends DivisionOwnerEntityService<Period> {

    constructor(http: Http, private intervalService: IntervalService) {
        super(http, 'periods')
    }

    convertFromServer(jsonResponse: any) {
        if (jsonResponse.intervalTimes != null) {
            for (let i = 0; i < jsonResponse.intervalTimes.length; i++) {
                this.intervalService.convertItemFromServer(jsonResponse.intervalTimes[i]);
            }
        }
    }

    convertToServer(period: Period): Period {
        const copy: Period = Object.assign({}, period);
        copy.intervalTimes = Object.assign([], period.intervalTimes);
        if (copy.intervalTimes != null) {
            for (let i = 0; i < copy.intervalTimes.length; i++) {
                copy.intervalTimes[i] = this.intervalService.convert(copy.intervalTimes[i]);
            }
        }
        return copy;
    }

}
