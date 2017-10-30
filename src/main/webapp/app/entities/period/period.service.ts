import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Period } from './period.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {IntervalService} from '../interval/interval.service';
import {createRequestOptionWithDivisionsId} from '../../shared/model/request-util';

@Injectable()
export class PeriodService {

    private resourceUrl = SERVER_API_URL + 'api/periods';
    private resourceByCurrentLoginUrl = SERVER_API_URL + 'api/periods/login';
    private resourceByDivisionsIdUrl = SERVER_API_URL +  'api/periods/divisions';

    constructor(private http: Http, private intervalService: IntervalService) {}

    create(period: Period): Observable<Period> {
        const copy = this.convert(period);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(period: Period): Observable<Period> {
        const copy = this.convert(period);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Period> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertInterval(jsonResponse);
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

    findByDivision(ids: number[], req?: any): Observable<ResponseWrapper> {
        return this.http.get(this.resourceByDivisionsIdUrl, createRequestOptionWithDivisionsId(ids, req))
            .map((res: Response) => this.convertResponse(res));
    }

    findByCurrentLogin(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceByCurrentLoginUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        this.convertInterval(jsonResponse);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertInterval(jsonResponse: any) {
        if (jsonResponse.intervalTimes != null) {
            for (let i = 0; i < jsonResponse.intervalTimes.length; i++) {
                this.intervalService.convertItemFromServer(jsonResponse.intervalTimes[i]);
            }
        }
    }

    private convert(period: Period): Period {
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
