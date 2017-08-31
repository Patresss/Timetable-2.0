import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {Interval} from '../entities/interval/interval.model';

@Pipe({
    name: 'intervalIncludedFilter',
    pure: false
})
@Injectable()
export class IntervalIncludedFilter implements PipeTransform {
    transform(intervals: Interval[], included: boolean): any {
        return intervals.filter((interval) => interval.included === included);
    }
}
