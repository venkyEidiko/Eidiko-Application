import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'firstletter'
})
export class FirstletterPipe implements PipeTransform {

  transform(value: string): string {
    if(!value){
      return '';
    }
    else{
      return value.charAt(0).toUpperCase();
    }
  }

}
