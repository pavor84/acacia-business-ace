package com.cosmos.acacia.gui;

/**
 * 
 * Created	:	13.12.2008
 * @author	Petar Milev
 * 
 * When using percent/value pairs like in ( {@link AcaciaPercentValueField} } we calculate the values
 * depending on a total value.
 * 
 * [value] = [total value] * [percent value] / 100.
 * 
 * In order to keep the value synchronized we need to listen for the 'total value' change, since 
 * it is not part of the component (nor its binding).
 *
 */
public interface TotalValueChangedListener {
    void totalValueChanged(Number newValue);
}
