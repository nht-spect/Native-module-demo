//
//  Counter.m
//  AwesomeProject
//
//  Created by thinh nguyen on 03/09/2022.
//

#import <Foundation/Foundation.h>
#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE (Counter, NSObject)

RCT_EXTERN_METHOD(increment:(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(decrement :(RCTPromiseResolveBlock)resolve reject: (RCTPromiseRejectBlock)reject)

@end
