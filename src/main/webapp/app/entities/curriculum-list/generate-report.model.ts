import {Timetable} from '../timetable';
import {WindowModel} from './window.model';

export class GenerateReportModel  {
    constructor(
        public unacceptedTimetables?: Timetable[],
        public windows?: WindowModel[],
        public numberOfWindows?: number,
        public numberOfHandicapAlgorithmIterations?: number,
        public numberOfSwapAlgorithmIterations?: number,
        public numberOfHandicapNearToBlockAlgorithmIterations?: number,
        public globalIterations?: number,
        public allTimeImMs?: number,
        public otherTimeImMs?: number,
        public generateTimeImMs?: number,
        public removeWindowsTimeImMs?: number,
        public generateTimeHandicapInWindowsAlgorithmImMs?: number,
        public generateTimeSwapInWindowAlgorithmImMs?: number,
        public generateTimeHandicapNearToBlockAlgorithmImMs?: number,
        public calculatePreferenceTimeImMs?: number,
        public calculateLessonAndTimeInMs?: number,
        public calculatePlaceTimeInMs?: number,
        public numberOfRemoveWindowsByHandicapInWindow?: number,
        public numberOfRemoveWindowsBySwapInWindow?: number,
        public numberOfRemoveHandicapNearToBlockInWindow?: number,
        public minPoint?: number,
        public maxPoints?: number,
        public averagePoints?: number,
        public medianPoints?: string
    ) {
    }
}
