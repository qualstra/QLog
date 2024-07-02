package com.enoch.model.checklist;

public enum QuestionType {
	// DONOT change the order as this will affect the db which is stores the index
	String, 	Date, 		Time,
	Number, 	Boolean, 	Location, 
	Option, 	OptionQue, 	OptionSec, 
	RepetativeQue, RepetativeSec
}
