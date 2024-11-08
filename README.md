# OneShotLagFix
## What's the problem with oneshot?
Basically, oneshot is trick of how velocity update is works in the game.
for some reason, sometimes CLIENT side player doesn't take knockback from dragon hitboxes, but server side player does.

At this point, player can take huge knockback from tiny dragon knockback hitbox. but if client side doesn't take knockback, it will cause position and velocity desync of player between client side and server side.

Problem is here. with this case, client player does stay on end main island but server player does fly away from main island. and also if player take so huge knockback, player could be literally fly away even over outer islands.

If you don't understand it, Curcuit made a [video](https://www.youtube.com/watch?v=SQrrpitg-Ts) about what will happen if client velocity and server velocity has completely synced.

This while, server does trying to generate ALL chunks which player will pass a way with current velocity for collision check.
sometimes player could have 1000+ blocks/tick velocity, then server will try to generate 62+ chunks in SINGLE tick.

Lastly, arrow from bow also have SAME velocity as player and same chunk generation behavior (because of entity/block hit check) at this point.

## How to fix it?
So this mod does change to if horizontal velocity of player/arrow is 16 block/tick or faster, 
it will modify velocity to not check to outside of render distance to player/arrow while checking collision or ray-casting.

Then server will not try to generate new chunks and it's fixes the oneshot lag.

Because of this, it does have different behaviors that compares with vanilla and may have the following side effects:
- Server side player doesn't get vanilla effect from velocity what have 16+ blocks/tick speed. it will not move to outside of render distance. but still velocity value of player is same as vanilla.
- Player collision check might not work properly with very fast velocity on unloaded chunks, could be pass through the wall, or something like that.
- Arrow collision check might not work properly with very fast velocity on unloaded chunks, could be pass through the wall and entity, or something like that.