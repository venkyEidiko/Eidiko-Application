import { FirstletterPipe } from '../pipes/firstletter.pipe';

describe('FirstletterPipe', () => {
  it('create an instance', () => {
    const pipe = new FirstletterPipe();
    expect(pipe).toBeTruthy();
  });
});
