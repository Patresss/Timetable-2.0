import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

@Injectable()
export class FillerService {

    private resourceUrl = SERVER_API_URL + 'api/fill';

    constructor(private http: Http) { }

    fill(): Observable<Response> {
        return this.http.post(this.resourceUrl, {}).map((res: Response) => {
            return res.json();
        });
    }

}
