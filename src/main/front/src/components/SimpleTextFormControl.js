import React from 'react';
import {FormControl, FormGroup} from 'react-bootstrap';

const SimpleTextFormControl = ({readOnly, field, placeHolderTxn, handleFieldChange, onKeyPress}) => {
    return (
        <FormGroup>
            <FormControl
                readOnly={readOnly}
                type="text"
                value={field}
                placeholder={placeHolderTxn}
                onChange={handleFieldChange}
                onKeyPress={onKeyPress}/>
            <FormControl.Feedback />
        </FormGroup>
    );
};

export default SimpleTextFormControl;
