AppClock.sched(0.0,{ arg time;

try {this.interpret(
"SynthDef(\"subtide_sc_sclang-test_my-synth26483\", {\n  arg freq=(440), amp=(0.5), pan=(0.0);\n  var env;\n  env = EnvGen.ar( Env( [0, 1, 1, 0], [0.01, 0.1, 0.2] ), doneAction: 2 );\n  Out.ar( 0, Pan2.ar( Blip.ar( freq ) * env * amp, pan ) );\n}).writeDefFile(\"resources/sc/synthdef\").add;\n\n0.exit;"
) }
  { |error|
    "

ERROR
----------------------------------
".postln;
    error.reportError;
    "-------------------------------------

".postln;
  };

});