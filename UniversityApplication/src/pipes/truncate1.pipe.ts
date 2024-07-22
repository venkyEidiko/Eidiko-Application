import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate1'
})
export class Truncate1Pipe implements PipeTransform {

  transform(value: string, limit: number = 30): string {
    if (!value) return '';
    return value.length > limit ? value.substring(0, limit) + '..' : value;
  }

}
