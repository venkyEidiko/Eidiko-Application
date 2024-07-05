import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'capatlize'
})
export class CapatlizePipe implements PipeTransform {

  transform(value: string): string {
    if(!value){
      return ''
    }
    else{
      const str = (value.charAt(0)).toUpperCase() + value.substring(1);
      return str;
    }
  }

}
