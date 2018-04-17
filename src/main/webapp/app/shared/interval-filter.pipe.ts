import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {Interval} from '../entities/interval/interval.model';

@Pipe({
    name: 'intervalIncludedFilter',
    pure: false
})
@Injectable()
export class IntervalIncludedFilter implements PipeTransform {
    transform(intervals: Interval[], includedState: boolean): any {
        if (intervals) {
            return intervals.filter((interval) => interval.includedState === includedState);
        } else {
            return [];
        }

    }
}
