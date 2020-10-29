package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   TestPawn.class,
   TestBishop.class,
   TestKnight.class,
   TestRook.class
})

public class JunitTestSuite {
}