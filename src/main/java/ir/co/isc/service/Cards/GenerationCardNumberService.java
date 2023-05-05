package ir.co.isc.service.Cards;

public class GenerationCardNumberService {
    /*  Cards card = new Cards();

      public String generateCardNumber(Cards issuerCode) {
          String binCards = card.request.g
          StringBuilder builder = new StringBuilder(String.valueOf(issuerCode));
          builder.append(binCards);
          int checkDigit = this.getCheckDigit(builder.toString());
          builder.append(checkDigit);

          return builder.toString();
      }
  */
    public int getCheckDigit(String cardNumber) {
        int sum = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int digit = Integer.parseInt(cardNumber.substring(i, (i + 1)));
            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }
}

