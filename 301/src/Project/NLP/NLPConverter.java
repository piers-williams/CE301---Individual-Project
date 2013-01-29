package Project.NLP;

import Project.Game.AI.SPL.Orders.SPLObject;

/**
 * Responsible for converting NL to SPL
 */
public interface NLPConverter {

      public SPLObject convert(String message);
}
