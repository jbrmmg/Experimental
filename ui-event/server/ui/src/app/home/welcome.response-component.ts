export interface IResponse {
    updateTime: Date;
}

export class Response implements IResponse {
    selected: boolean;

    constructor(public updateTime: Date) {
    }
}
