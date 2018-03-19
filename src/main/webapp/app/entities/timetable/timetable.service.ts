import {Injectable} from '@angular/core';
import {Http, Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {JhiDateUtils} from 'ng-jhipster';

import {Timetable} from './timetable.model';
import {ResponseWrapper} from '../../shared';
import {DateObject} from './date-object.model';
import {Time} from '../../util/time.model';
import {EntityService} from '../entity.service';

@Injectable()
export class TimetableService extends EntityService<Timetable> {

    constructor(http: Http, private dateUtils: JhiDateUtils) {
        super(http, 'timetables');
    }

    findByDateAndDivisionList(date: Date, divisionIdList: Array<number>): Observable<ResponseWrapper> {
        const urlSearchParams: URLSearchParams = new URLSearchParams();
        const dateObj = DateObject.fromDate(date);
        const dateToServer = this.dateUtils.convertLocalDateToServer(dateObj);
        urlSearchParams.set('date', dateToServer);
        urlSearchParams.set('divisionIdList', divisionIdList.toString());
        const options = {params: urlSearchParams};
        return this.http.get(`${this.resourceUrl}/division-list`, options).map((res: Response) => this.convertResponse(res));
    }

    findByDateAndTeacherId(date: Date, teacherId: number): Observable<ResponseWrapper> {
        const urlSearchParams: URLSearchParams = new URLSearchParams();
        const dateObj = DateObject.fromDate(date);
        const dateToServer = this.dateUtils.convertLocalDateToServer(dateObj);
        urlSearchParams.set('date', dateToServer);
        urlSearchParams.set('teacherId', teacherId.toString());
        const options = {params: urlSearchParams};
        return this.http.get(`${this.resourceUrl}/teacher`, options).map((res: Response) => this.convertResponse(res));
    }

    findByDateAndPlaceId(date: Date, placeId: number): Observable<ResponseWrapper> {
        const urlSearchParams: URLSearchParams = new URLSearchParams();
        const dateObj = DateObject.fromDate(date);
        const dateToServer = this.dateUtils.convertLocalDateToServer(dateObj);
        urlSearchParams.set('date', dateToServer);
        urlSearchParams.set('placeId', placeId.toString());
        const options = {params: urlSearchParams};
        return this.http.get(`${this.resourceUrl}/place`, options).map((res: Response) => this.convertResponse(res));
    }

    convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertEntity(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    convertEntity(entity: any) {
        entity.startDate = this.dateUtils.convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils.convertLocalDateFromServer(entity.endDate);
        entity.date = this.dateUtils.convertLocalDateFromServer(entity.date)
        if (entity.startTimeString) {
            entity.startTime = new Time(entity.startTimeString);
        }
        if (entity.endTimeString) {
            entity.endTime = new Time(entity.endTimeString);
        }
    }

    convert(timetable: Timetable): Timetable {
        const copy: Timetable = Object.assign({}, timetable);
        copy.startDate = this.dateUtils.convertLocalDateToServer(timetable.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(timetable.endDate);
        copy.date = this.dateUtils.convertLocalDateToServer(timetable.date);
        copy.startTimeString = timetable.startTime.formattedTime;
        copy.endTimeString = timetable.endTime.formattedTime;
        return copy;
    }
}
