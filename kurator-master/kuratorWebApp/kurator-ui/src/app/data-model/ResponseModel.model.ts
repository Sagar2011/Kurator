import { DocumentModel } from '../data-model/documentModel';

export interface ResponseModel {
    statusCode: number;
    message: string;
    result: DocumentModel[];
}
