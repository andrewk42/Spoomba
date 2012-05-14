SE382 Assignment 5 by Andrew Klamut
UID ajklamut

This is my take on the Spoomba Trainer. I developed it on my Windows XP partition with ~3gb RAM and on there it runs like butter. I also tried it on the VM with default settings (~512MB ram) and it still runs but a little choppy. I would prefer you run it on a decent non-VM system if you can.

The controls are pretty much as the assignment description says. Click on a part of the robot to select it. It'll change colour to cyan. For all parts, use the mouse wheel to rotate. For the claws, you can't rotate them, but trying to rotate them actually opens/closes them. For the body, you additionally use the keyboard arrow keys to translate.

I added an extension in my submission. Instead of having to hold arrow keys down to move the robot constantly, I made it more space-like (since the robot is supposed to be in space, right?) by making it so that you press an arrow key once to activate the robot's "thrusters" in that direction, and then you dont have to hold it. Plus the motion is handled by a separate thread (SwingWorker instance) so you can mess with the arms and collect stuff while it's moving.

Also, if it can count for any marks I think the code is pretty cleanly organized, at least compared to my previous submissions. Except maybe some of the Waldos class, that was the last part I worked on.

I hope you like it!


Thanks,

Andrew K
