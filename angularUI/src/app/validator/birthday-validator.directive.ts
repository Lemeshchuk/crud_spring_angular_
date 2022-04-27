import {Directive} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from '@angular/forms';

@Directive({
  selector: '[appBirthdayValidator]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: BirthdayValidatorDirective,
    multi: true
  }]
})
export class BirthdayValidatorDirective implements Validator {
  validate(control: AbstractControl): { [key: string]: any } | null {
    const date = new Date(control.value)

    if (!(date.getFullYear() >= 1940 && date.getFullYear() <= 2010)) {

      return {'birthdayInvalid': true};
    }
    return null;
  }
}
