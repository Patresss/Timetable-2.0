import {Injectable} from '@angular/core';
import {Http, Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {Timetable} from './timetable.model';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {DateObject} from './date-object.model';
import {Time} from '../../plan/time.model';

@Injectable()
export class TimetableService {

    private resourceUrl = SERVER_API_URL + 'api/timetables';

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
    }

    create(timetable: Timetable): Observable<Timetable> {
        const copy = this.convert(timetable);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(timetable: Timetable): Observable<Timetable> {
        const copy = this.convert(timetable);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Timetable> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByDateAndDivisionList(date: Date, divisionIdList: Array<number>): Observable<ResponseWrapper> {
        const urlSearchParams: URLSearchParams = new URLSearchParams();
        const dateObj = DateObject.fromDate(date);
        const dateToServer = this.dateUtils.convertLocalDateToServer(dateObj);
        urlSearchParams.set('date', dateToServer);
        urlSearchParams.set('divisionIdList', divisionIdList.toString());
        const options = {params: urlSearchParams};
        return this.http.get(`${this.resourceUrl}/divisionList`, options).map((res: Response) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils.convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils.convertLocalDateFromServer(entity.endDate);
        entity.date = this.dateUtils.convertLocalDateFromServer(entity.date);
        if (entity.startTime) {
            entity.startTime = new Time(entity.startTime);
        }
        if (entity.endTime) {
            entity.endTime = new Time(entity.endTime);
        }
    }

    private convert(timetable: Timetable): Timetable {
        const copy: Timetable = Object.assign({}, timetable);
        copy.startDate = this.dateUtils.convertLocalDateToServer(timetable.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(timetable.endDate);
        copy.date = this.dateUtils.convertLocalDateToServer(timetable.date);
        return copy;
    }
}
