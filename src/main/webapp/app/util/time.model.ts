export class Time {

    hour: number;
    minute: number;
    second: number;

    static createTimeFromTimePicker(timePicker: any) {
        return new Time(timePicker.hour + ':' + timePicker.minute);
    }

    constructor(stringForm: string) {
        this.hour = parseInt(stringForm.split(':')[0], 10);
        this.minute = parseInt(stringForm.split(':')[1], 10);
    }

    public getFormatted(): string {
        const hourString = this.hour.toString().length <= 1 ? '0' + this.hour : this.hour.toString();
        const minuteString = this.minute.toString().length <= 1 ? '0' + this.minute : this.minute.toString();
        return hourString + ':' + minuteString;
    }

    public getMinutes() {
        return 60 * this.hour + this.minute;
    }

}
