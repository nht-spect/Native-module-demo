

#import <Foundation/Foundation.h>
#import "RCTCalendarModule.h"
#import <React/RCTLog.h>
@implementation RCTCalendarModule


RCT_EXPORT_METHOD(createCalendarEvent:(RCTResponseSenderBlock)callback)
{
  NSInteger eventId = 2;
 callback(@[@(eventId)]);

 RCTLogInfo(@"Pretending to create an event");
}
RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(getName)
{
return [[UIDevice currentDevice] name];
}


// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE();

@end
