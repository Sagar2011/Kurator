export interface Reason {
    value: string;
    viewValue: string;
}

export const config = {
    flaggingReason: [
        { value: 'Authentication, Authorization issue', viewValue: 'Authentication, Authorization issue' },
        { value: 'Broken link or content not loading', viewValue: 'Broken link or content not loading' },
        { value: 'Contains explicit or inappropriate content', viewValue: 'Contains explicit or inappropriate content' },
        { value: 'Contains sensitive information', viewValue: 'Contains sensitive information' },
        { value: 'Issues related to privacy and copyright', viewValue: 'Issues related to privacy and copyright' },
        { value: 'Not relevant to intent/topic', viewValue: 'Not relevant to intent/topic' },
        { value: 'Outdated content', viewValue: 'Outdated content' },
        { value: 'Plagiarized content', viewValue: 'Plagiarized content' },
        { value: 'Unrelated or not useful', viewValue: 'Unrelated or not useful' },
        { value: 'Other', viewValue: 'Other' }
    ]
};
