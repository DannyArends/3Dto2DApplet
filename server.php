<?php

print("#FROM_CLIENT" . "\n");
foreach($_POST as $key => $value) {
	print("$key=$value" . "\n");
}

print("#SERVER_STATUS" . "\n");
print("V: 0.0.1" . "\n");
print("T: " . date("d.m.y H:m:s") . "\n");

print("#USER_STATUS"."\n");
print("L: 0" . "\n");
print("N: Anonymous" . "\n");

?>