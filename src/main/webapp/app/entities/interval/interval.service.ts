import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Interval } from './interval.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {createRequestOptionWithPeriodId} from "../../shared/model/request-util";

@Injectable()
export class IntervalService {

    private resourceUrl = 'api/intervals';
    private resourcePeriodUrl = 'api/intervals/period';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(interval: Interval): Observable<Interval> {
        const copy = this.convert(interval);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(interval: Interval): Observable<Interval> {
        const copy = this.convert(interval);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Interval> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    findByPeriodId(id: number, req?: any): Observable<ResponseWrapper> {
        return this.http.get(this.resourcePeriodUrl, createRequestOptionWithPeriodId(id, req))
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
    }

    private convert(interval: Interval): Interval {
        const copy: Interval = Object.assign({}, interval);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(interval.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(interval.endDate);
        return copy;
    }
}
